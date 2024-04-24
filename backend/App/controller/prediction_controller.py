from flask import Blueprint, request, jsonify
from App.ext import db
from App.model.location import Location

prediction_controller = Blueprint('prediction', __name__)

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

    # Return the prediction result in JSON format
    return jsonify({
        'latitude': lat,
        'longitude': long,
        'predicted_crowd_density': prediction
    })

# eg: http://127.0.0.1:5000/predict/predict?lat=40.7128&long=-74.0060
from flask import Blueprint

prediction_controller = Blueprint('prediction', __name__)
