from App.ext import db


class Location(db.Model):
    location_id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    lat = db.Column(db.Float)
    long = db.Column(db.Float)
    def str2json(self):
        return {
            "lat":self.lat,
            "long":self.long,
            "location_id":self.location_id
        }
