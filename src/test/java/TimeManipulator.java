import java.time.*;

public class TimeManipulator {

    // Given a clock fixed at 2020-05-24 14:00:00
    LocalDateTime datetime;
    Instant instant;
    Clock clock;


    TimeManipulator(){
        // When asking the "now" via this clock
        this.datetime = LocalDateTime.of(2020, 10, 24, 14, 0);
        this.instant = ZonedDateTime.of(datetime, ZoneId.systemDefault()).toInstant();
        this.clock =  Clock.fixed(instant, ZoneId.systemDefault());


    }

    LocalDateTime test(){
        LocalDateTime now = LocalDateTime.now(this.clock);
        System.out.println(now.toString());
        return now;
    }

    public static final void main(String[] args){

        TimeManipulator tm = new TimeManipulator();
        tm.test();
    }
}
