CREATE TABLE EventLog (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            ioId TEXT,
                            time INTEGER,
                            ioType TEXT,
                            msg TEXT
);