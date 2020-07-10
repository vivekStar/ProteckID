package com.webfarmatics.proteckapp.views;

import com.webfarmatics.proteckapp.model.Brand;
import com.webfarmatics.proteckapp.model.Category;
import com.webfarmatics.proteckapp.model.Model;
import com.webfarmatics.proteckapp.model.StrIssue;

import java.util.ArrayList;

public interface RaiseIssueIntr {

    void setBrands(ArrayList<Brand> brandList);

    void setBrandListError();

    void setModels(ArrayList<Model> modelList);

    void setModelListError();

    void setIssues(ArrayList<StrIssue> issueList);

    void setIssueListError();

    void setCategories(ArrayList<Category> categoryList);

    void setCategoryError();

    void onIssueSubmit();


}
