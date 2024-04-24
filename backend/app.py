from App import create_app

app = create_app()
if __name__ == '__main__':
    # app = Flask(__name__)

    app.run(debug=True)
