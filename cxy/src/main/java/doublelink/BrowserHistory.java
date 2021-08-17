package doublelink;

import java.util.Arrays;

/**
 * 你有一个只支持单个标签页的 浏览器，最开始你浏览的网页是 homepage，你可以访问其他的网站 url，也可以在浏览历史中后退 steps 步或前进 steps 步。
 *
 * 请你实现 BrowserHistory 类：
 *
 * BrowserHistory(string homepage)，用 homepage 初始化浏览器类。
 * void visit(string url) 从当前页跳转访问 url 对应的页面。执行此操作会把浏览历史前进的记录全部删除。
 * string back(int steps) 在浏览历史中后退 steps 步。如果你只能在浏览历史中后退至多 x 步且 steps > x，那么你只后退 x 步。请返回后退 至多 steps 步以后的 url 。
 * string forward(int steps) 在浏览历史中前进 steps 步。如果你只能在浏览历史中前进至多 x 步且 steps > x，那么你只前进 x 步。请返回前进 至多 steps步以后的 url。
 *
 * 提示：
 *
 * 1 <= homepage.length <= 20
 * 1 <= url.length <= 20
 * 1 <= steps <= 100
 * homepage 和 url 都只包含'.' 或者小写英文字母。
 * 最多调用 5000 次 visit， back 和 forward 函数。
 *
 * 链接：https://leetcode-cn.com/problems/design-browser-history
 *
 * 解题：
 * 1. 很自然的可以使用 double linked list
 * 2. 使用 array list 会更快, 但是要处理 resize
 */
public class BrowserHistory {
  ArrayList urls = new ArrayList(2500);

  public BrowserHistory(String homepage) {
    urls.visit(homepage);
  }

  public void visit(String url) {
    urls.visit(url);
  }

  public String back(int steps) {
    return urls.back(steps);
  }

  public String forward(int steps) {
    return urls.forward(steps);
  }

  static class ArrayList {
    private int size;
    private int next;
    private String[] urls;

    public ArrayList(int capacity) {
      this.urls = new String[capacity];
    }

    public void resize() {
      if (size >= urls.length) {
        urls = Arrays.copyOf(urls, urls.length * 2);
      }
    }

    public void visit(String url) {
      resize();
      urls[next++] = url;
      size = next;
    }

    public String back(int steps) {
      int index = Integer.max((next - 1) - steps, 0);
      next = index + 1;
      return urls[index];
    }

    public String forward(int steps) {
      next = Integer.min(next + steps, size);
      return urls[next - 1];
    }
  }

  /**
   * ["BrowserHistory","visit","visit","visit","back","back","forward","visit","forward","back","back"]
   * [["leetcode.com"],["google.com"],["facebook.com"],["youtube.com"],[1],[1],[1],["linkedin.com"],[2],[2],[7]]
   * @param args
   */
  public static void main(String[] args) {
    BrowserHistory history = new BrowserHistory("leetcode.com");
    history.visit("a");
    history.visit("b");
    history.visit("c");

    System.out.println(history.back(1));
    System.out.println(history.back(1));
    System.out.println(history.forward(1));

    history.visit("c");

    System.out.println(history.forward(2));
    System.out.println(history.back(2));
    System.out.println(history.back(7));
  }

}
