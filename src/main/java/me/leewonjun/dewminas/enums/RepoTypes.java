package me.leewonjun.dewminas.enums;

import me.leewonjun.dewminas.domains.RepositoryLink;

public enum RepoTypes {
    GITHUB(1);

    private int typeConst;

    private RepoTypes(int typeConst) {
        this.typeConst = typeConst;
    }
    public int getTypeConst() {return typeConst;}
}
