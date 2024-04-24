from flask_migrate import Migrate
from flask_sqlalchemy import SQLAlchemy
from flask_apscheduler import APScheduler

db = SQLAlchemy()
migrate = Migrate()


class Config(object):
    SCHEDULER_TIMEZONE = 'Asia/Shanghai'  # 配置时区
    SCHEDULER_API_ENABLED = True  # 添加API


scheduler = APScheduler()


def init_ext(app):
    # init db
    print("flag")
    db.init_app(app=app)
    migrate = Migrate(app, db)
    print("Begin the scheduler")
    scheduler.init_app(app)
    scheduler.start()
    print("End the scheduler")


db = SQLAlchemy()
migrate = Migrate()
