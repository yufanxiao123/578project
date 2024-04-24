def get_db_uri(dbinfo):
    ENGINE = dbinfo.get('ENGINE')
    DRIVER = dbinfo.get('DRIVER')
    USER = dbinfo.get('USER')
    PASSWORD = dbinfo.get('PASSWORD')
    HOST = dbinfo.get('HOST')
    PORT = dbinfo.get('PORT')
    NAME = dbinfo.get('NAME')
    if ENGINE == 'sqlite':
        return '{}:///{}'.format(ENGINE, NAME)
    else:
        return "{}+{}://{}:{}@{}:{}/{}".format(ENGINE, DRIVER, USER, PASSWORD, HOST, PORT, NAME)


class Config:
    DEBUG = False
    TESTING = False
    SQLALCHEMY_TRACK_MODIFICATIONS = True


class DevelopConfig(Config):
    DEBUG = True

    DATABSE = {
        'ENGINE': 'sqlite',
        'DRIVER': '',
        'USER': '',
        'PASSWORD': '',
        'HOST': '',
        'PORT': '',
        'NAME': '../database.db'
    }
    SQLALCHEMY_DATABASE_URI = get_db_uri(DATABSE)
    SCHEDULER_TIMEZONE = 'Asia/Shanghai'  # 配置时区
    SCHEDULER_API_ENABLED = True  # 添加API

envs = {
    'develop': DevelopConfig
}