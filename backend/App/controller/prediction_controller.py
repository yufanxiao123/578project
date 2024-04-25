from flask import Blueprint, request, jsonify
from App.ext import db
from App.model.location import Location
import openai
import re
import  random
from datetime import datetime, timedelta
openai.api_key = "sk-proj-VErkYQf1V0n6K2ZsLetPT3BlbkFJ1yjrdM26IIZOX9N2JdiL"
prediction_controller = Blueprint('prediction', __name__)
def extract_result(text):
    numbers = re.findall(r'\{(\d+)\}', text)
    return numbers
@prediction_controller.route('/predict', methods=['GET'])
def crowd():
    # Retrieve parameters
    lat = request.args.get('lat')
    long = request.args.get('long')

    # Check if both latitude and longitude parameters are provided
    if lat is None or long is None:
        return jsonify({'error': 'Missing latitude or longitude parameters'}), 400

    try:
        # Attempt to convert parameters to floats
        lat = float(lat)
        long = float(long)
    except ValueError:
        # If conversion fails, return an error message
        return jsonify({'error': 'Invalid latitude or longitude values. They must be real numbers.'}), 400

    # Ensure the latitude and longitude are within valid ranges
    if not (-90 <= lat <= 90) or not (-180 <= long <= 180):
        return jsonify({'error': 'Latitude must be between -90 and 90 and longitude must be between -180 and 180.'}), 400

    # Define the geographic boundary for the query
    lat_limit = [lat - 0.1, lat + 0.1]
    long_limit = [long - 0.1, long + 0.1]

    # Query the database for relevant location data within the specified boundary
    result = db.session.query(Location).filter(
        Location.lat >= lat_limit[0],
        Location.lat <= lat_limit[1],
        Location.long >= long_limit[0],
        Location.long <= long_limit[1]
    ).all()

    # Implement your prediction logic here
    # For demonstration purposes, let's assume we just return the number of entries
    prediction = len(result)
    prompt = "Now you act as a Covid-19 crowd predict specilist, please help me to decide the number of infections in each of the previous five hours. Please just randomly give the answers"
    prompt_few_shot = """
    Q:Given the Density: 100
    please help me to decide the number of infections:
    A: based on the density, the next 5 hours, the number of infections is:{32},{41},{77},{82},{91}
    Q:Given the Density:"""
    messages = [{"role": "system", "content": "You are an AI assistant that helps people find information."}]
    message_prompt = {"role": "user", "content": prompt_few_shot+prompt+str(prediction)+"please help me to decide the number of infections:\n A:"}
    messages.append(message_prompt)
    response = openai.ChatCompletion.create(
        model="gpt-3.5-turbo",
        messages=messages,
        temperature=0,
        max_tokens=256,
        top_p=1,
        frequency_penalty=0,
        presence_penalty=0)
    # print()
    results = response["choices"][0]['message']['content']
    print(results)
    results = extract_result(results)
    print(results)
    predictions = []
    # Return the prediction result in JSON format

    now = datetime.now()

    # 打印当前小时和接下来的之前五个小时
    for i in range(5):
        # 计算之前的小时
        previous_hour = now + timedelta(hours=i)
        # 格式化时间，仅显示小时和分钟
        formatted_time = previous_hour.strftime("%H:%M")
        print("当前时间向前推", i, "小时:", formatted_time)
    for i in range(5):
        # flag+=1
        previous_hour = now - timedelta(hours=i)
        # 格式化时间，仅显示小时和分钟
        formatted_time = previous_hour.strftime("%H:%M")
        # num = int(i)
        if results==[]:
            random_integer = random.randint(1, 100)
            predictions.append({
                'crowdness':random_integer,
                'time': str(formatted_time),
            })
        else:
            predictions.append({
                'crowdness': results[i],
                'time': str(formatted_time),
            })

    print(predictions)
    return jsonify(predictions)

# eg: http://127.0.0.1:5000/predict/predict?lat=40.7128&long=-74.0060
