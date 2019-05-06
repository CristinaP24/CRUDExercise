public class Main {
    public static void main(String[] args) {

        Crud file1 = new Crud("C:/Users/User/Desktop/CrudExercise/", "shop_test");
        file1.createRow("T-shirts", 18, 20);
        file1.createRow("Skirts", 30, 50);
        file1.printFile();
        System.out.println("--------------------------------------");

        file1.updateRow(2, "Jeans", 60,150);
        file1.deleteRow(1);
        file1.printFile();
        file1.closeFile();

        Crud file2 = new Crud("C:/Users/User/Desktop/CrudExercise/", "shop_test_2nd_File");

    }
}
