from flask import Blueprint
from App.ext import db

from App.model.location import Location
from App.model.new import News

news_controller = Blueprint('news', __name__)


@news_controller.route('')
def news():
    # t = News.query.all()
    result = db.session.query(News).limit(10).all()
    print(result)
    news_result = []
    flag = 0
    for i in result:
        flag += 1
        if flag == 1:
            continue
        news_result.append(i.str2json())
    return news_result
#
# @history_controller.route('/t')
# def news():
#     t = Location.query.all()
