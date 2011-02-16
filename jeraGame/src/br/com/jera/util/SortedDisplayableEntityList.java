package br.com.jera.util;

import java.util.LinkedList;
import java.util.ListIterator;

public class SortedDisplayableEntityList extends LinkedList<DisplayableEntity> {

	private static final long serialVersionUID = 1L;

	public void draw(SceneViewer viewer, SpriteResourceManager res) {
		ListIterator<DisplayableEntity> iter = super.listIterator();
		while (iter.hasNext()) {
			DisplayableEntity ent = iter.next();
			ent.draw(viewer, res);
		}
		super.clear();
	}

	@Override
	public boolean add(DisplayableEntity o) {
		ListIterator<DisplayableEntity> iter = super.listIterator();

		// TODO verificar se este é o algoritmo mais rápido
		DisplayableEntity last;
		if (iter.hasNext()) {
			last = iter.next();
			if (o.compareTo(last) <= 0) {
				super.addFirst(o);
				return true;
			}
		} else {
			super.addLast(o);
			return true;
		}

		while (iter.hasNext()) {
			DisplayableEntity cmp = last;
			last = iter.next();
			if (o.compareTo(cmp) > 0 && o.compareTo(last) <= 0) {
				iter.previous();
				iter.add(o);
				return true;
			}
		}
		super.add(o);
		return true;
	}
}
