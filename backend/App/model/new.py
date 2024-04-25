from sqlalchemy import func

from App.ext import db


class News(db.Model):
    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    title = db.Column(db.String())
    image = db.Column(db.String())
    content = db.Column(db.String())
    date = db.Column(db.String())

    def str2json(self):
        return {
            # "id": self.id,
            "title": self.title,
            "image": self.image,
            "content": self.content,
            # "date": self.date
        }
