export interface Transaction {
    id: number,
    sourceAccountId: string;
    destinationAccountId: string;
    amount: number;
    transactionType: string;
    transactionDate: string;
    description: string;
  }
  