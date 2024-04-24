from flask import Flask
from flask_apscheduler import APScheduler
import pgeocode

import time
from App.ext import scheduler
from App.ext import db
from App.model.location import Location
import random
# interval example, 间隔执行, 每10秒执行一次
def generate_random_coordinates():
    latitude = random.uniform(-90, 90)
    longitude = random.uniform(-180, 180)
    return latitude, longitude


def convert_zipcode_to_loc(zipcode):
    zipcode = str(zipcode)
    nomi = pgeocode.Nominatim('us')
    query = nomi.query_postal_code(zipcode)

    data = {
        "lat": query["latitude"],
        "lon": query["longitude"]
    }
    return data

def generate_random_coordinates_near_loc(lat,long):

    center_latitude, center_longitude = lat, long

    # 生成一个小的随机偏移，例如在中心点周围最多偏移0.01度
    offset_max = 0.01
    latitude = center_latitude + random.uniform(-offset_max, offset_max)
    longitude = center_longitude + random.uniform(-offset_max, offset_max)

    return latitude, longitude


@scheduler.task('interval', id='task_1', seconds=2, misfire_grace_time=900)
def task1():
    t_l = []
    for i in range(0,10):
        lat,long = generate_random_coordinates()
        t = Location()
        t.lat = lat
        t.long = long
        t_l.append(t)
    with scheduler.app.app_context():
        db.session.add_all(t_l)
        db.session.commit()

    # result = db.session.query(Location).filter(Location.lat >= lat_limit[0],
    #                                            Location.lat <= lat_limit[1],
    #                                            Location.long >= long_limit[0],
    #                                            Location.long <= long_limit[1]).all()
    print('task 1 executed --------', time.time())


# cron examples, 每5秒执行一次 相当于interval 间隔调度中seconds = 5
# @scheduler.task('cron', id='task_2', second='*/20')
# def task2():
#     print('`task 2 executed --------`', time.time())
#
#
# if __name__ == '__main__':
#     app = Flask(__name__)
#     app.config.from_object(Config())
#     scheduler.init_app(app)
#     scheduler.start()
#     app.run()
@scheduler.task('interval', id='task_2', seconds=2, misfire_grace_time=900)
def task_more_safe_crowd():
    t_l = []
    for i in range(0,10):
        lat, long = generate_random_coordinates()
        t = Location()
        t.lat = lat
        t.long = long
        t_l.append(t)
        for i in range(0,5):
            lat_new,long_new = generate_random_coordinates_near_loc(lat, long)
            t = Location()
            t.lat = lat_new
            t.long = long_new
            t_l.append(t)
    with scheduler.app.app_context():
        db.session.add_all(t_l)
        db.session.commit()
    print('task 2 executed --------', time.time())
