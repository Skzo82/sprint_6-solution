public class Main {
    public static void main(String[] args) {
        HistoryManager historyManager = new InMemoryHistoryManager();

        Task task1 = new Task(1, "Task 1");
        Task task2 = new Task(2, "Task 2");
        Task task3 = new Task(3, "Task 3");

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task1);
        historyManager.add(task3);

        System.out.println("History after adding tasks: " + historyManager.getHistory());

        historyManager.remove(2);
        System.out.println("History after removing task 2: " + historyManager.getHistory());
    }
}