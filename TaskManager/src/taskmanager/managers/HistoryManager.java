package taskmanager.managers;

import taskmanager.tasks.Task;

import java.util.List;


public interface HistoryManager {
    void add(Task task);
    List<Task> getHistory();
}
