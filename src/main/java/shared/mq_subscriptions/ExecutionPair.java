package shared.mq_subscriptions;


public class ExecutionPair {
    String UUID;
    Exec exec;

    public ExecutionPair(String UUID, Exec exec) {
        this.UUID = UUID;
        this.exec = exec;
    }

}