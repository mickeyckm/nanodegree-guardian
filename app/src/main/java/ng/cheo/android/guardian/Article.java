package ng.cheo.android.guardian;

/**
 * Created by mickey on 6/11/16.
 */

public class Article {

    private String mName;
    private String mSection;
    private String mWebUrl;

    public Article(String name, String section) {
        mName = name;
        mSection = section;
        mWebUrl = "";
    }

    public String getName() {
        return mName;
    }

    public String getSection() {
        return mSection;
    }

    public String getWebUrl() {
        return mWebUrl;
    }

    public void setWebUrl(String webUrl) {
        mWebUrl = webUrl;
    }

}
