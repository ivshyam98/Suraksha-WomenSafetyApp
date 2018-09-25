from __future__ import print_function
import base64
import json
import os
import urllib
from urllib import request, parse




def build_speechlet_response(title, output, reprompt_text, should_end_session):
    return {
        'outputSpeech': {
            'type': 'PlainText',
            'text': output
        },
        'card': {
            'type': 'Simple',
            'title': "SessionSpeechlet - " + title,
            'content': "SessionSpeechlet - " + output
        },
        'reprompt': {
            'outputSpeech': {
                'type': 'PlainText',
                'text': reprompt_text
            }
        },
        'shouldEndSession': should_end_session
    }


def build_response(session_attributes, speechlet_response):
    return {
        'version': '1.0',
        'sessionAttributes': session_attributes,
        'response': speechlet_response
    }


# --------------- Functions that control the skill's behavior ------------------

def get_welcome_response():
    """ If we wanted to initialize the session to have some attributes we could
    add those here
    """

    session_attributes = {}
    card_title = "Welcome, this is Emma"
    speech_output = "Welcome, this is Emma, your safety is very important to me. When every your feeling unsafe just tell me."
                   
    # If the user either does not reply to the welcome message or says something
    # that is not understood, they will be prompted again with this text.
    reprompt_text = "Welcome, this is Emma, your safety is very important to me. When every your feeling unsafe just tell me. " 
                    
    should_end_session = False
    return build_response(session_attributes, build_speechlet_response(
        card_title, speech_output, reprompt_text, should_end_session))


def handle_session_end_request():
    card_title = "Session Ended"
    speech_output = "Thank you for trying Suraksha" \
                    "Have a nice day! "
    # Setting this to true ends the session and exits the skill.
    should_end_session = True
    return build_response({}, build_speechlet_response(
        card_title, speech_output, None, should_end_session))
   

def create_favorite_color_attributes(favorite_color):                  
    return {"favoriteColor": favorite_color}

def send_message_alerts():             
    from firebase import firebase
    firebase = firebase.FirebaseApplication('https://socialloginandroid-86b22.firebaseio.com/', None)
    result = firebase.get('/users/9k1z9VTXRra3mEnJexC4T3WTyza2', None)
    phoneno1=result['phone1']
    phoneno2=result['phone2']
    print(phoneno1,phoneno2)
    


    
    
    #return phoneno1
    import boto3
    client = boto3.client(
    "sns",
    aws_access_key_id="MISSING",
    aws_secret_access_key="MISSING+MISSING+MISSING",
    region_name="us-east-1"
    )
     
    client.publish(
    PhoneNumber=phoneno2,
    Message="Your relative/friend is in danger, this was invoked from her amazon echo",
    MessageAttributes={
            'AWS.SNS.SMS.SenderID': {
                'DataType': 'String',
                'StringValue': 'SENDERID'
            },
            'AWS.SNS.SMS.SMSType': {
                'DataType': 'String',
                'StringValue': 'Transactional'
            }
        }
    )
    





# --------------- Events ------------------

def on_session_started(session_started_request, session):
    """ Called when the session starts """

    print("on_session_started requestId=" + session_started_request['requestId']
          + ", sessionId=" + session['sessionId'])


def on_launch(launch_request, session):
    """ Called when the user launches the skill without specifying what they
    want
    """

    print("on_launch requestId=" + launch_request['requestId'] +
          ", sessionId=" + session['sessionId'])
    # Dispatch to your skill's launch
    return get_welcome_response()


def on_intent(intent_request, session):
    """ Called when the user specifies an intent for this skill """

    print("on_intent requestId=" + intent_request['requestId'] +
          ", sessionId=" + session['sessionId'])

    intent = intent_request['intent']
    intent_name = intent_request['intent']['name']
    
    if intent_name == "unsafe":
            send_message_alerts()
            session_attributes = {}
            card_title = "Welcome, this is Emma"
            speech_output = "Calling police, Connected with police , Police on the way. Police will be in 1 min . Your relatives and frieds are all informed. Help Me, Help Me, Help Me ,Help Me, Help Me, Help Me, Help Me, Help Me ,Help Me, Help Me, Help Me, Help Me, Help Me"
                   
             # If the user either does not reply to the welcome message or says something
             # that is not understood, they will be prompted again with this text.
            reprompt_text = "Help Me, Help Me, Help Me ,Help Me, Help Me, Help Me, Help Me, Help Me ,Help Me, Help Me, Help Me, Help Me, Help Me, Help Me, Help Me, Help Me ,Help Me, Help Me, Help Me, Help Me, Help Me ,Help Me, Help Me, Help Me, Help Me, Help Me "
                    
            should_end_session = False
            return build_response(session_attributes, build_speechlet_response(
                    card_title, speech_output, reprompt_text, should_end_session))
        
        
            
    elif intent_name == "AMAZON.HelpIntent":
        return get_welcome_response()
    elif intent_name == "AMAZON.CancelIntent" or intent_name == "AMAZON.StopIntent":
        return handle_session_end_request()
    else:
        raise ValueError("Invalid intent")


def on_session_ended(session_ended_request, session):
    """ Called when the user ends the session.

    Is not called when the skill returns should_end_session=true
    """
    print("on_session_ended requestId=" + session_ended_request['requestId'] +
          ", sessionId=" + session['sessionId'])
    # add cleanup logic here


# --------------- Main handler ------------------

def lambda_handler(event, context):
    """ Route the incoming request based on type (LaunchRequest, IntentRequest,
    etc.) The JSON body of the request is provided in the event parameter.
    """
    print("event.session.application.applicationId=" +
          event['session']['application']['applicationId'])

    """
    Uncomment this if statement and populate with your skill's application ID to
    prevent someone else from configuring a skill that sends requests to this
    function.
    """
    # if (event['session']['application']['applicationId'] !=
    #         "amzn1.echo-sdk-ams.app.[unique-value-here]"):
    #     raise ValueError("Invalid Application ID")

    if event['session']['new']:
        on_session_started({'requestId': event['request']['requestId']},
                           event['session'])

    if event['request']['type'] == "LaunchRequest":
        return on_launch(event['request'], event['session'])
    elif event['request']['type'] == "IntentRequest":
        return on_intent(event['request'], event['session'])
    elif event['request']['type'] == "SessionEndedRequest":
        return on_session_ended(event['request'], event['session'])
