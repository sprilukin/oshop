module.exports = {
    "production": {
        "database": {
            "dbName": "dbname",
            "user": "username",
            "password": "password",
            "host": "localhost",
            "dialect": "postgres",
            "pool": {
                "max": 5,
                "min": 0,
                "idle": 10000
            }
        }
    },

    "development": {
        "database": {
            "dbName": "dbname",
            "user": "username",
            "password": "password",
            "host": "localhost",
            "dialect": "postgres",
            "pool": {
                "max": 5,
                "min": 0,
                "idle": 10000
            }
        }
    },

    "test": {
        "database": {
            "dbName": "dbname",
            "user": "username",
            "password": "password",
            "host": "localhost",
            "dialect": "postgres",
            "pool": {
                "max": 5,
                "min": 0,
                "idle": 10000
            }
        }
    }
};