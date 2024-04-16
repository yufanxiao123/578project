from App.ext import db


class Location(db.Model):
    location_id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    lat = db.Column(db.Float)
