
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class Person {

    public static HashMap<Integer, String> emailProviders = new HashMap<>();

    String uuid;
    String firstname;
    String lastname;
    String email;
    String phone;
    String birthdate;
    int year_joined;
    boolean is_member;

    public Person(String firstname, String lastname) throws IOException, ParseException {
        this.uuid = createUUID();
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = createEmail();
        this.phone = createPhone();
        this.birthdate = createBirthdate();
        setMemberStatus(); // sets year_joined and is_member
    }

    public static void setEmailProviders() {
        Person.emailProviders.put(0, "@gmail.com");
        Person.emailProviders.put(1, "@yahoo.com");
        Person.emailProviders.put(2, "@icloud.com");
        Person.emailProviders.put(3, "@xfinity.com");
        Person.emailProviders.put(4, "@outlook.com");
    }

    private String createUUID() {
        return uuid = UUID.randomUUID().toString();
    }

    private String createEmail() {
        Random rand = new Random();
        String provider = emailProviders.get(rand.nextInt(5));
        StringBuilder sb = new StringBuilder(this.firstname);
        sb.append(".").append(this.lastname).append(provider);
        return sb.toString().toLowerCase();
    }

    private String createPhone() {
        // https://gist.github.com/joeyv/7087747
        int num1, num2, num3;
        int set2, set3;
        Random generator = new Random();
        num1 = generator.nextInt(7) + 1;
        num2 = generator.nextInt(8);
        num3 = generator.nextInt(8);
        set2 = generator.nextInt(643) + 100;
        set3 = generator.nextInt(8999) + 1000;
        return "(" + num1 + "" + num2 + "" + num3 + ")" + "-" + set2 + "-" + set3;
    }

    private String createBirthdate() throws ParseException {
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int year = randBetween(gc.getWeekYear() - 80, gc.getWeekYear());
        gc.set(GregorianCalendar.YEAR, year);
        int dayOfYear = randBetween(1, gc.getActualMaximum(GregorianCalendar.DAY_OF_YEAR));
        gc.set(GregorianCalendar.DAY_OF_YEAR, dayOfYear);
        String stringDate = gc.get(GregorianCalendar.YEAR) + "-" + (gc.get(GregorianCalendar.MONTH) + 1) + "-" + gc.get(GregorianCalendar.DAY_OF_MONTH);

        Date date = sdf.parse(stringDate);
        return sdf.format(date);
    }

    private static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    private void setMemberStatus() {
        GregorianCalendar gc = new GregorianCalendar();
        int currentYear = gc.getWeekYear();

        Random rand = new Random();
        int x = rand.nextInt(100) + 1;
        this.is_member = (x < 90);
        this.year_joined = currentYear - rand.nextInt(25);
    }

    @Override
    public String toString() {
        return this.uuid + "\n"
                + this.firstname + " " + this.lastname + "\n"
                + this.email + "\n"
                + this.phone + "\n"
                + this.birthdate + "\n";
    }

}
