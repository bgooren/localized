package de.malkusch.localized.event;

import java.lang.reflect.Field;

import org.hibernate.StatelessSession;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.persister.entity.EntityPersister;

import de.malkusch.localized.Localized;
import de.malkusch.localized.LocalizedIntegrator;
import de.malkusch.localized.LocalizedProperty;
import de.malkusch.localized.exception.LocalizedException;

/**
 * Remove the entity's @{@link Localized} fields.
 * 
 * @author Markus Malkusch <markus@malkusch.de>
 * @since 0.2.8
 */
public class DeleteEventListener extends AbstractEventListener implements PostDeleteEventListener {

	private static final long serialVersionUID = -4669372532737725066L;

	public DeleteEventListener(LocalizedIntegrator integrator, SessionFactoryImplementor sessionFactory) {
		super(integrator, sessionFactory);
	}

	@Override
	protected void handleField(StatelessSession session, Field field,
			Object entity, LocalizedProperty property)
			throws LocalizedException {
		if (property.getId() == null) {
			return;
			
		}
		session.delete(property);
	}

	@Override
	public void onPostDelete(PostDeleteEvent event) {
		handleFields(event, event.getEntity(), event.getId());
	}

	/**
	 * XXX No idea what this means
	 */
	@Override
	public boolean requiresPostCommitHanding(EntityPersister persister) {
		return true;
	}

}
