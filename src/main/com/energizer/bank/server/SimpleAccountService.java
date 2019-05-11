package com.energizer.bank.server;

class SimpleAccountService implements AccountService {

    @Override
    public void withdraw(int dollars, Account account) throws NotEnoughMoneyException, NotValidMoneyInputException {

       if (dollars <=0) {throw new NotValidMoneyInputException();}

        if (account instanceof DepositAccount) {
            if (account.getDollars() < dollars){throw new NotEnoughMoneyException();}
            account.setDollars(account.getDollars()-dollars);
             } else if (account instanceof CreditAccount){
            if (account.getDollars() + ((CreditAccount) account).getCreditDollars() < dollars) {throw new NotEnoughMoneyException();}
            else if (account.getDollars()>=dollars){account.setDollars(account.getDollars()-dollars);}
                else {
                    int creditVariable = (((CreditAccount) account).getCreditDollars() + account.getDollars() - dollars);
                    account.setDollars(0);
                    ((CreditAccount) account).setCreditDollars(creditVariable);
            }
        }
    }

    @Override
    public void deposit(int dollars, Account account) throws NotValidMoneyInputException {
        if (dollars<=0){throw new NotValidMoneyInputException();}

        if (account instanceof DepositAccount){
            account.setDollars(account.getDollars()+dollars);
        }else if (account instanceof CreditAccount){
            if (((CreditAccount) account).getTmpCredit() != ((CreditAccount) account).getCreditDollars()){
                int dolg = (((CreditAccount) account).getTmpCredit()-((CreditAccount) account).getCreditDollars());
                ((CreditAccount) account).setCreditDollars(((CreditAccount) account).getCreditDollars()+dolg);
                account.setDollars(account.getDollars()+dollars-dolg);
            }else{ account.setDollars(account.getDollars()+dollars);}
        }
    }

    @Override
    public void transfer(int dollars, Account from, Account to) throws NotEnoughMoneyException, NotValidMoneyInputException {
      withdraw(dollars, from);
      deposit(dollars, to);
    }


}
