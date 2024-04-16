from App.controller.prediction_controller import prediction_controller
from App.controller.history_controller import history_controller


def init_blueprint(app):
    app.register_blueprint(blueprint=history_controller, url_prefix='/history')
    app.register_blueprint(blueprint=prediction_controller, url_prefix='/predict')
