package geun.jpastudy.application.required;

import geun.jpastudy.domain.onetoone.Member_;

import java.util.List;

public interface Member_RepoEx {
    List<Member_> containName(String name);
}
