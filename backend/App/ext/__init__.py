from flask_migrate import Migrate
from flask_sqlalchemy import SQLAlchemy

db = SQLAlchemy()
migrate = Migrate()


def init_ext(app):
    # init db
    db.init_app(app=app)
    migrate = Migrate(app, db)
