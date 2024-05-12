public class Educators {
    protected int numOfQuiz;
    protected int numOfEvent;
    
    public Educators(){
        this.numOfEvent = 0;
        this.numOfQuiz = 0;
    }

    public int getNumOfQuiz() {
        return numOfQuiz;
    }

    public void setNumOfQuiz(int numOfQuiz) {
        this.numOfQuiz = numOfQuiz;
    }

    public int getNumOfEvent() {
        return numOfEvent;
    }

    public void setNumOfEvent(int numOfEvent) {
        this.numOfEvent = numOfEvent;
    }

    @Override
    public String toString() {
        return "Educators{" +
                "numOfQuiz=" + numOfQuiz +
                ", numOfEvent=" + numOfEvent +
                '}';
    }
}
