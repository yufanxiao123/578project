from flask import Blueprint

from App.ext import db
from App.model.location import Location
from App.model.history import History

history_controller = Blueprint('history', __name__)


@history_controller.route('')
def history():
    result = db.session.query(History).limit(10).all()
    ret = []
    for i in result:
        ret.append(i.str2json())
    print(ret)
    return ret


