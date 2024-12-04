public class Task {
    private final int id;
    private final String title;

    public Task(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Task{id=" + id + ", title='" + title + "'}";
    }
}
