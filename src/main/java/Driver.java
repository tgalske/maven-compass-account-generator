

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class Driver {

    static int numAccounts;
    static String requestUrl;
    static RequestHandler client = new RequestHandler();
    static boolean createJsonFile = true;
    static FileWriter fw = null;
    static BufferedWriter bw = null;

    public static void main(String[] args) throws IOException, ParseException {

        createUrl();
        Person.setEmailProviders();
        setupFile();
        String apiJson = client.send(requestUrl);
        JSONArray jsonarray = new JSONArray(apiJson);
        JSONObject obj = new JSONObject();
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            String firstname = jsonobject.getString("name");
            String lastname = jsonobject.getString("surname");
            Person p = new Person(firstname, lastname);
            obj.put("id", p.uuid);
            obj.put("firstname", p.firstname);
            obj.put("lastname", p.lastname);
            obj.put("email", p.email);
            obj.put("phone", p.phone);
            obj.put("birthdate", p.birthdate);
            obj.put("year_joined", p.year_joined);
            obj.put("is_member", p.is_member);
            bw.write(obj.toString() + "\n");

            obj = new JSONObject();
        }
        closeFile();

        System.out.println(numAccounts + " accounts generated.");
    }

    static void createUrl() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of accounts to create: ");
        numAccounts = sc.nextInt();

        requestUrl = "https://uinames.com/api/?region=united%20states&amount="
                + numAccounts;
        System.out.println("Please wait...\n");
    }

    static void setupFile() {
        DateFormat dateFormat = new SimpleDateFormat("hhmma_MM-dd-yyyy");
        Date date = new Date();
        String filename = dateFormat.format(date) + ".json";

        try {
            fw = new FileWriter(filename);
            bw = new BufferedWriter(fw);

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    static void closeFile() {
        try {
            if (bw != null) {
                bw.close();
            }
            if (fw != null) {
                fw.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
