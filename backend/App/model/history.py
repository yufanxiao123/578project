from App.ext import db


class History(db.Model):
    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    date = db.Column(db.String(20), unique=True, nullable=False)
    number = db.Column(db.Integer, nullable=False)

    def str2json(self):
        return {
            "id": self.id, "date": self.date, "number": self.number
        }
