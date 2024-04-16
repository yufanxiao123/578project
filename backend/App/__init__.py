from flask import Flask
from flask_cors import CORS

from App.config import envs
from App.controller import init_blueprint
from App.ext import init_ext


def create_app():
    app = Flask(__name__)
    app.config.from_object(envs.get('develop'))
    init_ext(app=app)
    CORS(app)

    init_blueprint(app)
    return app
