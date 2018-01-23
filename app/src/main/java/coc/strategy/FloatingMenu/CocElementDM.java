package coc.strategy.FloatingMenu;

/**
 * Created by XTO on 18/01/2018.
 */

public class CocElementDM
{
    int drawableResource = 0;
    boolean isNormal = false;
    boolean isDark = false;
    boolean isSpell = false;
    boolean isHeroe = false;
    int space = 0;

    public int getDrawableResource()
    {
        return drawableResource;
    }

    public CocElementDM(int drawableResource)
    {
        this.drawableResource = drawableResource;
    }

    public void setNormal(boolean normal)
    {
        isNormal = normal;
        unsetRest();
    }

    public void setDark(boolean dark)
    {
        isDark = dark;
        unsetRest();
    }

    public void setHeroe(boolean heroe)
    {
        isHeroe = heroe;
        unsetRest();
    }

    public void setSpell(boolean spell)
    {
        isSpell = spell;
        unsetRest();
    }
    private void unsetRest()
    {
        if(isNormal)
        {
            isDark = false;
            isHeroe = false;
            isSpell = false;
        }
        if(isDark)
        {
            isNormal = false;
            isHeroe = false;
            isSpell = false;
        }
        if(isHeroe)
        {
            isNormal = false;
            isDark = false;
            isSpell = false;
        }
        if(isSpell)
        {
            isNormal = false;
            isDark = false;
            isHeroe = false;
        }
    }
    public boolean getIsDark()
    {
        return isDark;
    }
    public boolean getIsSpell()
    {
        return isSpell;
    }
    public boolean getIsNormal()
    {
        return isNormal;
    }
    public boolean getIsHeroe()
    {
        return isHeroe;
    }
}
