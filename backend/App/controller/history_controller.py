from flask import Blueprint

from App.model.location import Location
from App.model.new import News

history_controller = Blueprint('history', __name__)


@history_controller.route('/v')
def history():
    t = News.query.all()


@history_controller.route('/t')
def history1():
    t = Location.query.all()
