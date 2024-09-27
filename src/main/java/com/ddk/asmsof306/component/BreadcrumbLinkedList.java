package com.ddk.asmsof306.component;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BreadcrumbLinkedList {

    private BreadcrumbNode head;

    public void pushBreadcrumb(Breadcrumb breadcrumb) {
        BreadcrumbNode newNode = new BreadcrumbNode(breadcrumb);
        if (head != null) {
            newNode.setNext(head);
        }
        head = newNode;
    }

    public Breadcrumb popBreadcrumb() {
        if (head == null) {
            return null;
        }
        BreadcrumbNode node = head;
        head = head.getNext();
        return node.getBreadcrumb();
    }


    public List<Breadcrumb> getBreadcrumbs() {
        List<Breadcrumb> breadcrumbs = new ArrayList<>();
        BreadcrumbNode currentNode = head;
        while (currentNode != null) {
            breadcrumbs.add(currentNode.getBreadcrumb());
            currentNode = currentNode.getNext();
        }
        return breadcrumbs;
    }
}
