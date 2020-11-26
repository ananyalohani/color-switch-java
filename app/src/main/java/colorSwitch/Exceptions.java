class LabelExistsException extends Exception {
    LabelExistsException(String message) {
        super(message);
    }
}

class InvalidInputException extends Exception {
    InvalidInputException(String message) {
        super(message);
    }
}

class GameSavedException extends Exception {
    GameSavedException(String message) {
        super(message);
    }
}
