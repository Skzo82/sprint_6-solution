package taskmanager.test;

import taskmanager.managers.InMemoryTaskManager;
import taskmanager.managers.TaskManager;
import taskmanager.tasks.Task;
import taskmanager.tasks.Epic;
import taskmanager.tasks.Subtask;
import taskmanager.tasks.TaskStatus;
import taskmanager.managers.HistoryManager;
import taskmanager.managers.Managers;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;

public class InMemoryTaskManagerTest {

    private InMemoryTaskManager taskManager;

    @BeforeEach
    public void setup() {
        taskManager = new InMemoryTaskManager();
    }

    @Test
    public void testAddAndRetrieveTask() {
        Task task = new Task("Test Task", "This is a test task.");
        int taskId = taskManager.addNewTask(task);

        Task retrievedTask = taskManager.getTask(taskId);
        assertNotNull(retrievedTask, "Task should not be null.");
        assertEquals(task, retrievedTask, "Tasks should be equal.");
    }

    @Test
    public void testHistoryPreservesPreviousVersion() {
        Task task = new Task("Task1", "Description1");
        int id = taskManager.addNewTask(task);
        task.setDescription("Updated Description");
        taskManager.updateTask(new Task(task));

        List<Task> history = taskManager.getHistory();
        assertTrue(history.contains(task), "История должна содержать обновленную задачу.");
    }

    @Test
    public void testHistoryLimit() {
        for (int i = 0; i < 15; i++) {
            Task task = new Task("Task " + i, "Description " + i);
            taskManager.addNewTask(task);
            taskManager.getTask(task.getId());
        }

        List<Task> history = taskManager.getHistory();
        assertEquals(10, history.size(), "History should contain only the last 10 tasks.");
    }

    @Test
    public void testAddNewEpicAndSubtask() {
        Epic epic = new Epic("Epic", "Description of Epic");
        int epicId = taskManager.addNewEpic(epic);

        Subtask subtask = new Subtask("Subtask", "Description of Subtask", epicId);
        int subtaskId = taskManager.addNewSubtask(subtask);

        assertNotNull(taskManager.getEpic(epicId), "Epic должен быть найден.");
        assertNotNull(taskManager.getSubtask(subtaskId), "Subtask должен быть найден.");
    }

    @Test
    public void testRemoveTaskFromHistory() {
        Task task = new Task("Test Task", "This is a test task.");
        int taskId = taskManager.addNewTask(task);
        taskManager.getTask(taskId);
        taskManager.removeTask(taskId);

        List<Task> history = taskManager.getHistory();
        assertFalse(history.contains(task), "Task should not be in history after removal.");
    }

    @Test
    public void testRemoveEpicAndSubtasksFromHistory() {
        Epic epic = new Epic("Epic", "Description of Epic");
        int epicId = taskManager.addNewEpic(epic);

        Subtask subtask1 = new Subtask("Subtask1", "Description of Subtask1", epicId);
        taskManager.addNewSubtask(subtask1);
        Subtask subtask2 = new Subtask("Subtask2", "Description of Subtask2", epicId);
        taskManager.addNewSubtask(subtask2);

        taskManager.getEpic(epicId);
        taskManager.removeEpic(epicId);

        List<Task> history = taskManager.getHistory();
        assertFalse(history.contains(epic), "Epic should not be in history after removal.");
        assertFalse(history.contains(subtask1), "Subtask1 should not be in history after removal.");
        assertFalse(history.contains(subtask2), "Subtask2 should not be in history after removal.");
    }
}
