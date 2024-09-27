package com.ddk.asmsof306.component;

public class BreadcrumbNode {
    private Breadcrumb breadcrumb;
    private BreadcrumbNode next;

    public BreadcrumbNode(Breadcrumb breadcrumb) {
        this.breadcrumb = breadcrumb;
    }

    public Breadcrumb getBreadcrumb() {
        return breadcrumb;
    }

    public BreadcrumbNode getNext() {
        return next;
    }

    public void setNext(BreadcrumbNode next) {
        this.next = next;
    }
}