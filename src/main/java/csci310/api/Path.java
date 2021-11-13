package csci310.api;

import csci310.exception.RequestException;

public class Path {
    private final String[] parts;

    public Path(String[] parts) {
        this.parts = parts;
    }

    public Path(String path) {
        String stripped = path.replaceAll("/+$|^/+", "");
        if (stripped.length() == 0) {
            this.parts = new String[] {};
        } else {
            this.parts = stripped.split("/+");
        }
    }

    public int size() {
        return this.parts.length;
    }

    public String at(int index) {
        return this.parts[index];
    }

    public int id(int index) throws RequestException {
        try {
            return Integer.parseInt(this.parts[index]);
        } catch (NumberFormatException exception) {
            throw new RequestException(400, "could not parse ID at index " + index);
        }
    }
}
