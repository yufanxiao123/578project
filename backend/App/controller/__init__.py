from App.controller.crowd_controller import crowd_controller
from App.controller.prediction_controller import prediction_controller
from App.controller.news_controller import news_controller


def init_blueprint(app):
    app.register_blueprint(blueprint=news_controller, url_prefix='/news')
    app.register_blueprint(blueprint=prediction_controller, url_prefix='/predict')
    app.register_blueprint(blueprint=crowd_controller, url_prefix='/crowd')
from App.controller.prediction_controller import prediction_controller
from App.controller.history_controller import history_controller


def init_blueprint(app):
    app.register_blueprint(blueprint=history_controller, url_prefix='/history')
    app.register_blueprint(blueprint=prediction_controller, url_prefix='/predict')
