package com.energizer.bank.server;

class SimpleAccountService implements AccountService {

    @Override
    public void withdraw(int dollars, Account account) throws NotEnoughMoneyException, NotValidMoneyInputException {
       if (dollars <=0) {throw new NotValidMoneyInputException();}

        if (account instanceof DepositAccount) {
            if (account.dollars < dollars){throw new NotEnoughMoneyException();}
            account.dollars = (account.dollars-dollars);

        } else if (account instanceof CreditAccount){
            if (account.dollars + ((CreditAccount) account).creditDollars<= dollars) {throw new NotEnoughMoneyException();}
            else if (account.dollars>=dollars){account.dollars = (account.dollars-dollars);}
                else {
                    int creditVariable = (((CreditAccount) account).creditDollars + account.dollars - dollars);
                    account.dollars = 0;
                    ((CreditAccount) account).creditDollars= creditVariable;
            }
        }
    }

    @Override
    public void deposit(int dollars, Account account) throws NotEnoughMoneyException, NotValidMoneyInputException {
        if (dollars<=0){throw new NotValidMoneyInputException();}

        if (account instanceof DepositAccount){
            account.dollars=account.dollars+dollars;
        }else if (account instanceof CreditAccount){
            if (((CreditAccount) account).tmpCredit != ((CreditAccount) account).creditDollars){
                int dolg = (((CreditAccount) account).tmpCredit-((CreditAccount) account).creditDollars);
                ((CreditAccount) account).creditDollars = (((CreditAccount) account).creditDollars+dolg);
                account.dollars = account.dollars+dollars-dolg;
            }else{ account.dollars = account.dollars+dollars;}
        }
    }

    @Override
    public void transfer(int dollars, Account from, Account to) throws NotEnoughMoneyException, NotValidMoneyInputException {
      withdraw(dollars, from);
      deposit(dollars, to);
    }
}
