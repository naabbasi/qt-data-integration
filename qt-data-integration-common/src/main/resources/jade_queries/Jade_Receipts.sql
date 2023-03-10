select 'Corporate' BusinessUnit,
'BANK TT RECEIPT' ReceiptMethod,
'0013-192809-002' RemiitanceBankAccountNumber,
receipt_no ReceiptNumber,
s.Amount ReceiptAmount,
DATE_FORMAT(invoice_date, '%d/%m/%Y %h:%i%p') ReceiptDate,
DATE_FORMAT(invoice_date, '%d/%m/%Y %h:%i%p') AccountingDate,
'CASH CUSTOMER JADE' CustomerName,
'100001' CustomerAccountNumber,
receipt_no TransactionNumber,
DATE_FORMAT(invoice_date, '%d/%m/%Y %h:%i%p') ApplyDate,
s.Amount  AppliedAmount,
'' Comments,
'' receiptLocation,
'' actualCustomerName,
'' chequeTtNumber,
'' bankDrawnOn,
'' referenceDocNo,
'' voyageNumber,
'' activity,
'' department,
'' costCenter,
'' creditCardAuthonicationNumber,
'' posAuthonicationNumber,
'' createdBy,
'' issuerName,
DATE_FORMAT(invoice_date, '%d/%m/%Y %h:%i%p') issueDate
FROM jade_cash_invoice s
where s.Amount > 0