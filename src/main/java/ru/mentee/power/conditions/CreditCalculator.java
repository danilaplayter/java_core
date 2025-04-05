package ru.mentee.power.conditions;

import java.util.Scanner;

public class CreditCalculator {

    static final int BASE_INTEREST_RATE_MORTGAGE = 9;
    static final int BASE_INTEREST_RATE_CONSUMER = 15;
    static final int BASE_INTEREST_RATE_AUTO = 12;
    static final int CREDIT_RATING_BAD = 3;
    static final int CREDIT_RATING_MID = 0;
    static final int CREDIT_RATING_GOOD = -1;
    static final int CREDIT_RATING_EXCELLENT = -2;

    public double calculateMonthlyPayment(double loanAmount, int loanTermMonths, String creditType, int creditScore) {

        double monthlyRate = 0;

        if(creditType.equals("Ипотека")){
            monthlyRate += BASE_INTEREST_RATE_MORTGAGE;
        }
        if(creditType.equals("Потребительский")){
            monthlyRate += BASE_INTEREST_RATE_CONSUMER;
        }
        if(creditType.equals("Автокредит")){
            monthlyRate += BASE_INTEREST_RATE_AUTO;
        }
        if(!creditType.equals("Ипотека") && !creditType.equals("Потребительский") && !creditType.equals("Автокредит")){
            return -1;
        }
        if(creditScore >= 750 && creditScore <= 850){
            monthlyRate += CREDIT_RATING_EXCELLENT;
        }
        if(creditScore >= 650 && creditScore <= 749){
            monthlyRate += CREDIT_RATING_GOOD;
        }
        if(creditScore >= 500 && creditScore <= 649){
            monthlyRate += CREDIT_RATING_MID;
        }
        if(creditScore >= 300 && creditScore <= 499){
            monthlyRate += CREDIT_RATING_BAD;
        }

        monthlyRate = (monthlyRate/12/100);

        if(!(creditScore >= 750 && creditScore <= 850) && !(creditScore >= 650 && creditScore <= 749) && !(creditScore >= 500 && creditScore <= 649) && !(creditScore >= 300 && creditScore <= 499)){
            return -1;
        }

        if(!(loanTermMonths > 0 && loanTermMonths <= 360)){
            return -1;
        }

        if(loanAmount > 10000 && loanAmount < 10000000){
            return  loanAmount * (monthlyRate * Math.pow(1 + monthlyRate, loanTermMonths)) / (Math.pow(1 + monthlyRate, loanTermMonths) - 1);
        }
        else {
            return -1;
        }

    }

    public static void main(String[] args) {
        CreditCalculator calculator = new CreditCalculator();
        Scanner scanner = new Scanner(System.in);

        double loanAmount;
        int loanTermMonths;
        String creditType;
        int creditScore;

        System.out.println("Здравствуйте! Введите следующие данные.");
        System.out.print("Введите сумму кредита: ");
        loanAmount = scanner.nextDouble();
        System.out.print("Введите срок кредита в месяцах: ");
        loanTermMonths = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Введите тип кредита: ");
        creditType = scanner.nextLine();
        System.out.print("Введите кредитный рейтинг: ");
        creditScore = scanner.nextInt();
        System.out.println("Ежемесячный платёж: " + calculator.calculateMonthlyPayment(loanAmount, loanTermMonths, creditType, creditScore));

        scanner.close();
    }

}
