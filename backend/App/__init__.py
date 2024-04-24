from flask import Flask
from flask_cors import CORS

from App.config import envs
from App.controller import init_blueprint
from App.ext import init_ext
from App.controller.prediction_controller import prediction_controller
# from .extensions import db, migrate  # Assuming you have these extensions
from App.Generation_Task import generator


def create_app():
    app = Flask(__name__)
    app.config.from_object(envs.get('develop'))
    init_ext(app=app)
    CORS(app)
    init_blueprint(app)
    return app
