package business.result;

public class ProductResult {

    public enum PRODUCT_RESULT {ADDED_PRODUCT, REMOVED_PRODUCT, UPDATED_PRODUCT, ERROR, EXIST}

    private ProductResult.PRODUCT_RESULT productResult;
    private String message;

    public PRODUCT_RESULT getProductResult() {
        return productResult;
    }

    public void setProductResult(PRODUCT_RESULT productResult) {
        this.productResult = productResult;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
