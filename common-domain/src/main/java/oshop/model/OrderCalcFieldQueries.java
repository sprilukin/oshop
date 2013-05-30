package oshop.model;

public class OrderCalcFieldQueries {

    public static final String CURRENT_ORDER_STATE_NAME_SQL =
            "( SELECT s.name FROM order_has_order_states h INNER JOIN order_state s ON h.order_state_id = s.id " +
            "WHERE h.order_id = id ORDER BY h.date DESC, h.id DESC LIMIT 1 )";

    public static final String CURRENT_ORDER_STATE_DATE_SQL =
            "( SELECT h.date FROM order_has_order_states h INNER JOIN order_state s ON h.order_state_id = s.id " +
            "WHERE h.order_id = id ORDER BY h.date DESC, h.id DESC LIMIT 1 )";

    public static final String PRODUCTS_COUNT_SQL =
            "( SELECT count(p.product_id) FROM order_products p WHERE p.order_id = id )";

    public static final String PRODUCTS_PRICE_SQL =
            "( SELECT sum(p.price) FROM order_products o INNER JOIN product p on o.product_id = p.id WHERE o.order_id = id )";

    private static final String DISCOUNT_AMOUNT_SQL =
            "(SELECT d.amount FROM discount d INNER JOIN orders o on o.discount_id = d.id WHERE o.id = id)";

    private static final String DISCOUNT_TYPE_PERCENT_SQL =
            "(SELECT d.type FROM discount d INNER JOIN orders o on o.discount_id = d.id WHERE o.id = id) = 0";

    private static final String ADDITIONAL_PAYMENTS_AMOUNT_SQL =
            "(SELECT a.amount FROM additional_payment a INNER JOIN orders o on o.additional_payment_id = a.id WHERE o.id = id)";

    public static final String TOTAL_PRICE_SQL =
            "( SELECT " + PRODUCTS_PRICE_SQL + " + " +
                    "(CASE WHEN " + ADDITIONAL_PAYMENTS_AMOUNT_SQL + " IS NULL THEN 0 ELSE " + ADDITIONAL_PAYMENTS_AMOUNT_SQL + " END) - " +
                    "(CASE WHEN " + DISCOUNT_AMOUNT_SQL + " IS NULL THEN 0 ELSE (" +
                        "(CASE WHEN " + DISCOUNT_TYPE_PERCENT_SQL + " THEN (" + PRODUCTS_PRICE_SQL + " * " + DISCOUNT_AMOUNT_SQL + " / 100) ELSE " + DISCOUNT_AMOUNT_SQL + " END)" +
                    ") END) " +
            ")";
}
