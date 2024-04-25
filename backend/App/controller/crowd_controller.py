from flask import Blueprint, request

from App.ext import db
from App.model.location import Location

crowd_controller = Blueprint('crowd', __name__)



@crowd_controller.route('/')
def crowd():
    lat = float(request.args.get('lat'))
    long = float(request.args.get('long'))
    lat_limit = [lat - 0.1, lat + 0.1]
    long_limit = [long - 0.1, long + 0.1]
    result = db.session.query(Location).filter(Location.lat >= lat_limit[0],
                                               Location.lat <= lat_limit[1],
                                               Location.long >= long_limit[0],
                                               Location.long <= long_limit[1]).all()
    loc_result = []
    for i in result:
        loc_result.append(i.str2json())
    print(loc_result)

    return loc_result
