SELECT
    '',
    'QT_JADE INVOICE',
    'QT_CASH INVOICE',
    '',
    DATE_FORMAT(invoice_date, '%d/%m/%Y %h:%i%p'),
    DATE_FORMAT(invoice_date, '%d/%m/%Y %h:%i%p'),
    a.receipt_no,
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '100001',
    '100001',
    '',
    '',
    '',
    '',
    '',
    'LINE',
    a.tariff,
    'QAR',
    'User',
    DATE_FORMAT(invoice_date, '%d/%m/%Y %h:%i%p'),
    '1',
    a.lineamount,
    '1',
    '',
    a.lineamount,
    '',
    'QT_AUTO INVOICE',
    CONCAT(DATE_FORMAT(now(), '%M%y'), (@row_number:=@row_number + 1)),
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    'EA',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    a.glcode,
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    ''
FROM
    (SELECT
         s.`invoice_date` AS invoice_date,
         receipt_no,
         'Terminal Gate Weighbridge' AS tariff,
         CASE
             WHEN receipt_no LIKE 'CT1%' THEN '1001.202.0000.312070'
             ELSE '1001.203.0000.341160'
             END glcode,
         Column1 AS lineamount
     FROM
         jade_cash_invoice s
     WHERE
             Column1 > 0 UNION ALL SELECT
                                       s.`invoice_date` AS invoice_date,
                                       receipt_no,
                                       'C- Documentation charges' AS tariff,
                                       CASE
                                           WHEN receipt_no LIKE 'CT1%' THEN '1001.202.0000.371010'
                                           ELSE '1001.203.0000.371010'
                                           END glcode,
                                       Column2 AS lineamount
     FROM
         jade_cash_invoice s
     WHERE
             Column2 > 0 UNION ALL SELECT
                                       s.`invoice_date` AS invoice_date,
                                       receipt_no,
                                       'C- X-Ray Inspection for General cargo' AS tariff,
                                       CASE
                                           WHEN receipt_no LIKE 'CT1%' THEN ''
                                           ELSE ''
                                           END glcode,
                                       Column3 AS lineamount
     FROM
         jade_cash_invoice s
     WHERE
             Column3 > 0 UNION ALL SELECT
                                       s.`invoice_date` AS invoice_date,
                                       receipt_no,
                                       'C- X-Ray Inspection for CFS & GCT warehouses' AS tariff,
                                       CASE
                                           WHEN receipt_no LIKE 'CT1%' THEN '1004.240.0000.330010'
                                           ELSE ''
                                           END glcode,
                                       Column4 AS lineamount
     FROM
         jade_cash_invoice s
     WHERE
             Column4 > 0 UNION ALL SELECT
                                       s.`invoice_date` AS invoice_date,
                                       receipt_no,
                                       'Gen Set Rental Charges' AS tariff,
                                       CASE
                                           WHEN receipt_no LIKE 'CT1%' THEN ''
                                           ELSE ''
                                           END glcode,
                                       Column5 AS lineamount
     FROM
         jade_cash_invoice s
     WHERE
             Column5 > 0 UNION ALL SELECT
                                       s.`invoice_date` AS invoice_date,
                                       receipt_no,
                                       'MISC Charges' AS tariff,
                                       CASE
                                           WHEN receipt_no LIKE 'CT1%' THEN ''
                                           ELSE '1001.203.0000.341180'
                                           END glcode,
                                       Column6 AS lineamount
     FROM
         jade_cash_invoice s
     WHERE
             Column6 > 0 UNION ALL SELECT
                                       s.`invoice_date` AS invoice_date,
                                       receipt_no,
                                       'Telescopic Mobile Crane 50 to 60 Tons Minimum 2 hours' AS tariff,
                                       CASE
                                           WHEN receipt_no LIKE 'CT1%' THEN '1001.202.0000.312020'
                                           ELSE '1001.203.0000.341140'
                                           END glcode,
                                       Column7 AS lineamount
     FROM
         jade_cash_invoice s
     WHERE
             Column7 > 0 UNION ALL SELECT
                                       s.`invoice_date` AS invoice_date,
                                       receipt_no,
                                       'Delivery of Rubber Tyre Vehicles up to 30 FT' AS tariff,
                                       CASE
                                           WHEN receipt_no LIKE 'CT1%' THEN ''
                                           ELSE '1001.203.0000.341190'
                                           END glcode,
                                       Column8 AS lineamount
     FROM
         jade_cash_invoice s
     WHERE
             Column8 > 0 UNION ALL SELECT
                                       s.`invoice_date` AS invoice_date,
                                       receipt_no,
                                       'Strage Normal Period Rubber Tyre/Chain Units more 30 Volume' AS tariff,
                                       CASE
                                           WHEN receipt_no LIKE 'CT1%' THEN ''
                                           ELSE '1001.203.0000.341070'
                                           END glcode,
                                       Column9 AS lineamount
     FROM
         jade_cash_invoice s
     WHERE
             Column9 > 0 UNION ALL SELECT
                                       s.`invoice_date` AS invoice_date,
                                       receipt_no,
                                       'Delivery of Chain units more than 30 FT' AS tariff,
                                       CASE
                                           WHEN receipt_no LIKE 'CT1%' THEN ''
                                           ELSE '1001.203.0000.341190'
                                           END glcode,
                                       Column10 AS lineamount
     FROM
         jade_cash_invoice s
     WHERE
             Column10 > 0 UNION ALL SELECT
                                        s.`invoice_date` AS invoice_date,
                                        receipt_no,
                                        'Strage Normal Period Rubber Tyre/Chain Units more 30 Volume' AS tariff,
                                        CASE
                                            WHEN receipt_no LIKE 'CT1%' THEN ''
                                            ELSE '1001.203.0000.341070'
                                            END glcode,
                                        Column11 AS lineamount
     FROM
         jade_cash_invoice s
     WHERE
             Column11 > 0 UNION ALL SELECT
                                        s.`invoice_date` AS invoice_date,
                                        receipt_no,
                                        'Mafi Pakages Handling' AS tariff,
                                        CASE
                                            WHEN receipt_no LIKE 'CT1%' THEN ''
                                            ELSE ''
                                            END glcode,
                                        Column12 AS lineamount
     FROM
         jade_cash_invoice s
     WHERE
             Column12 > 0 UNION ALL SELECT
                                        s.`invoice_date` AS invoice_date,
                                        receipt_no,
                                        'Mafi Packages Storage' AS tariff,
                                        CASE
                                            WHEN receipt_no LIKE 'CT1%' THEN ''
                                            ELSE ''
                                            END glcode,
                                        Column13 AS lineamount
     FROM
         jade_cash_invoice s
     WHERE
             Column13 > 0 UNION ALL SELECT
                                        s.`invoice_date` AS invoice_date,
                                        receipt_no,
                                        'Delivery of Static and HH Cargo per FT' AS tariff,
                                        CASE
                                            WHEN receipt_no LIKE 'CT1%' THEN ''
                                            ELSE '1001.203.0000.341190'
                                            END glcode,
                                        Column14 AS lineamount
     FROM
         jade_cash_invoice s
     WHERE
             Column14 > 0 UNION ALL SELECT
                                        s.`invoice_date` AS invoice_date,
                                        receipt_no,
                                        'Strage Normal period for Static and HH Cargo per Volume' AS tariff,
                                        CASE
                                            WHEN receipt_no LIKE 'CT1%' THEN ''
                                            ELSE '1001.203.0000.341070'
                                            END glcode,
                                        Column15 AS lineamount
     FROM
         jade_cash_invoice s
     WHERE
             Column15 > 0 UNION ALL SELECT
                                        s.`invoice_date` AS invoice_date,
                                        receipt_no,
                                        'InDirect Delivery Handling charges' AS tariff,
                                        CASE
                                            WHEN receipt_no LIKE 'CT1%' THEN '1001.202.0000.330070'
                                            ELSE '1001.203.0000.341010'
                                            END glcode,
                                        Column16 AS lineamount
     FROM
         jade_cash_invoice s
     WHERE
             Column16 > 0 UNION ALL SELECT
                                        s.`invoice_date` AS invoice_date,
                                        receipt_no,
                                        'RORO Packages Handling' AS tariff,
                                        CASE
                                            WHEN receipt_no LIKE 'CT1%' THEN ''
                                            ELSE ''
                                            END glcode,
                                        Column17 AS lineamount
     FROM
         jade_cash_invoice s
     WHERE
             Column17 > 0 UNION ALL SELECT
                                        s.`invoice_date` AS invoice_date,
                                        receipt_no,
                                        'Stor perday–CFS w/house-Per vol first 5 Days(aft free days)' AS tariff,
                                        CASE
                                            WHEN receipt_no LIKE 'CT1%' THEN '1001.202.0000.330040'
                                            ELSE ''
                                            END glcode,
                                        Column18 AS lineamount
     FROM
         jade_cash_invoice s
     WHERE
             Column18 > 0 UNION ALL SELECT
                                        s.`invoice_date` AS invoice_date,
                                        receipt_no,
                                        'RORO Packages Storage' AS tariff,
                                        CASE
                                            WHEN receipt_no LIKE 'CT1%' THEN ''
                                            ELSE ''
                                            END glcode,
                                        Column19 AS lineamount
     FROM
         jade_cash_invoice s
     WHERE
             Column19 > 0 UNION ALL SELECT
                                        s.`invoice_date` AS invoice_date,
                                        receipt_no,
                                        'InDirect Delivery Handling charges' AS tariff,
                                        CASE
                                            WHEN receipt_no LIKE 'CT1%' THEN ''
                                            ELSE '1001.203.0000.341010'
                                            END glcode,
                                        Column20 AS lineamount
     FROM
         jade_cash_invoice s
     WHERE
             Column20 > 0 UNION ALL SELECT
                                        s.`invoice_date` AS invoice_date,
                                        receipt_no,
                                        'General cargo Normal Storage period (Open Yard) per Volume' AS tariff,
                                        CASE
                                            WHEN receipt_no LIKE 'CT1%' THEN ''
                                            ELSE '1001.203.0000.341060'
                                            END glcode,
                                        Column21 AS lineamount
     FROM
         jade_cash_invoice s
     WHERE
             Column21 > 0 UNION ALL SELECT
                                        s.`invoice_date` AS invoice_date,
                                        receipt_no,
                                        'Port Handling Charges' AS tariff,
                                        CASE
                                            WHEN receipt_no LIKE 'CT1%' THEN '1001.202.0000.312060'
                                            ELSE '1001.203.0000.341010'
                                            END glcode,
                                        Column22 AS lineamount
     FROM
         jade_cash_invoice s
     WHERE
             Column22 > 0 UNION ALL SELECT
                                        s.`invoice_date` AS invoice_date,
                                        receipt_no,
                                        'Container Storage' AS tariff,
                                        CASE
                                            WHEN receipt_no LIKE 'CT1%' THEN ''
                                            ELSE '1001.203.0000.312010'
                                            END glcode,
                                        Column23 AS lineamount
     FROM
         jade_cash_invoice s
     WHERE
             Column23 > 0 UNION ALL SELECT
                                        s.`invoice_date` AS invoice_date,
                                        receipt_no,
                                        'Reefer' AS tariff,
                                        CASE
                                            WHEN receipt_no LIKE 'CT1%' THEN ''
                                            ELSE ''
                                            END glcode,
                                        Column24 AS lineamount
     FROM
         jade_cash_invoice s
     WHERE
             Column24 > 0 UNION ALL SELECT
                                        s.`invoice_date` AS invoice_date,
                                        receipt_no,
                                        'Shifting within CFS warehouses Per FT' AS tariff,
                                        CASE
                                            WHEN receipt_no LIKE 'CT1%' THEN '1001.202.0000.330060'
                                            ELSE '1001.203.0000.341180'
                                            END glcode,
                                        Column25 AS lineamount
     FROM
         jade_cash_invoice s
     WHERE
             Column25 > 0 UNION ALL SELECT
                                        s.`invoice_date` AS invoice_date,
                                        receipt_no,
                                        'C- Labor' AS tariff,
                                        CASE
                                            WHEN receipt_no LIKE 'CT1%' THEN '1001.202.0000.312030'
                                            ELSE '1001.203.0000.341150'
                                            END glcode,
                                        Column26 AS lineamount
     FROM
         jade_cash_invoice s
     WHERE
             Column26 > 0 UNION ALL SELECT
                                        s.`invoice_date` AS invoice_date,
                                        receipt_no,
                                        'LS. Handling for Sheep or goats Per head' AS tariff,
                                        CASE
                                            WHEN receipt_no LIKE 'CT1%' THEN ''
                                            ELSE '1001.203.0000.341050'
                                            END glcode,
                                        Column27 AS lineamount
     FROM
         jade_cash_invoice s
     WHERE
             Column27 > 0 UNION ALL SELECT
                                        s.`invoice_date` AS invoice_date,
                                        receipt_no,
                                        'Change of port of discharge and  or carrier' AS tariff,
                                        CASE
                                            WHEN receipt_no LIKE 'CT1%' THEN '1001.202.0000.312080'
                                            ELSE '1001.203.0000.341180'
                                            END glcode,
                                        Column28 AS lineamount
     FROM
         jade_cash_invoice s
     WHERE
             Column28 > 0) a

ORDER BY receipt_no , 4;