package com.boco.eoms.message.test.dao;

import java.util.List;

import com.boco.eoms.base.test.dao.BaseDaoTestCase;
import com.boco.eoms.message.model.SmsMobilesTemplate;
import com.boco.eoms.message.dao.ISmsMobilesTemplateDao;

import org.springframework.orm.ObjectRetrievalFailureException;

public class SmsMobilesTemplateDaoTest extends BaseDaoTestCase {
    private String smsMobilesTemplateId = new String("1");
    private ISmsMobilesTemplateDao dao = null;

    public void setSmsMobilesTemplateDao(ISmsMobilesTemplateDao dao) {
        this.dao = dao;
    }

    public void testAddSmsMobilesTemplate() throws Exception {
        SmsMobilesTemplate smsMobilesTemplate = new SmsMobilesTemplate();

        // set required fields

        java.lang.String mobiles = "VyCpDrVhAmBkPsSqScTyDiVjIrMgBpYhVzWhVbSyLzIpNoUmNtGmKgAxNlYpOkQpHaDeWyRqCdOsVnWxZzUiFfPyUdJnKwUbEsUtXhCpZiFkGoHiBrVvSsXxQwQeBgQcKmFgHdZtUnZjVjEwZhXwOoCoRqFlClBbXzByXyWtBnTfVwTuWdGyWdSiNiToTtOpUyDaTmRwTuUkVsLmPtZiArJiWaTkQiDhRlKlByIqYjGqFfVuWuByCzOcAmYxVqMeOgNeVbBfWbVfVgEeJyFyAmWwFcGrAzNeOiJpEtEjEwSzTxUqMhGySaMfUyQvIsEcHwCjKxGpKrVdOvDmBsOgNrRqFaVgRhGhDjZeWiKlSfFuUeQqMoWoXfYqIvKwFvToZyEbMdSdWhTeKsMyYzFpQyZyDgCjIwGyEeAwGgElOdFtWeSwAuHmRcFwQpJdIeUjTpCyBfNvVeGtEtNmLfWvWyMhWwGiBeSwShJbXdVtVxBaKdYpGpRkQuVlJjNgChApYwNtCcUhWeZbFmTgBjHzWqUhTpVaYsQvXjOoXnQbPnKdUsKgOiKkEjVwPdJoOiMfAzXxJeEiNaCbBhOfKdNyJhXeNdTpFbCkXhEbMcZhVzNqCyPsHcMuCsLaRtBdJeCtIpXeLtCsFkQyQrMtBdNbZvMkGbCeCeQnUlByFxTxHgTmDkWiQaTrQyEyJdZkMqAzCtVfLaJsAeVvXfOmLyKvHuIoFhDmOmVdMrYyPnYxLnXdInYjRgRpKjLmMpDnFmAmLfYlEdLuSmSlWwZsQkVoQpUrWxCnMzNzQqVtJeVfHlSuPoEqEhJfEtWyZtKkDuYlBnYlWoXjPwIuOcEhOrPzBsDdOrHcInKaOvEoZuPlLcGiEzBhNzGaZoVtUvDbWyAdHfHuBeTfLoGrVzLaOjUyFsCiTvQtFvFjAvPyVkPrWhDuUjNjOvIyVwQzRtMoEwLvZyUrPhZkKnYpHmIqOvLcFoBcQmDcSvHsLjDkAzSwUrHcErMpSaRzSsCgHiVjTcPdJpUoPaXwXpEdGkYdUfTmPqOpEtBrTnYcJpOaHzYrVyZuKmEuPrTxLeXfXmCgBcNxZnSuFgMxNvMwPtLrVpPfHaUyAvRpRmXoVcAbWcSbNwMpKzMtEyXuQoBgMgGuJgKuQsJhMxWaYiKuQvJdMgXdIoXrHdHeYuOuXfFuNsEzQoJaDiNvMdSzQoFcKaTdRcWxGuMgObSnVsDeDiDsNrYoYbJqSqZmFfQtTaXxIxWaUrXdLnWpRbKiHjBoNtUoMmKlTiBqFcQfUeTaEmLsYuBsLkMnGtUeClHjPqJlRrUbLjQfUxBkWwHfZxVdCzWfTsVmAbScCxPxDlQzZjNcLgQqYuVoHtFaDeVcMhTfZcFvVqLhKaZgAoEyLxSrLdDsWqSmVuDjIzGgZhYtYwSeRhWsPvLeGsPsUiWvHmJpYiQuWlIxAyIgVvQoTbCbEpSuNaPxQtNcQqNjBkBvLaXgZeZhDpNoTvOgFdWgPgJeCcPoBuOtXjWiUlQmFiEvWxHbPjRcOrXkFbTiNrTgTzRkKeSlAcNmKnJwEiNpOdWgWmUrHzEiRuAyXhVcEwEvCuHoZlAuJbQpNqKaBhYcAjBvJuRnJbVuRmIpBiBlYmMuOhQiYrKmEqDqRmWkCiXwGpOjSdYrFeUvJxGuRzUhCePxXyTxQiAgXbGwRiWeWnVuCeQtQdCiMlYxZtZuGaDlNbMlRsQhRhNqOwVwObIdOhBnJbPqRyJyBeVeEnRtUrSbCuTgDiYqUzYmChGpVzWhTwPcObHlPvLuCdWjCqRcKgPvHgXzThBfVpAwCfCjKkSyOyWpKbFoHnXlLeEsThZeInYtUsUbZnEuXzTpSvQhDrIaLlYgDgWcJdNrAbAaPsMuBrDqIgTmAuDlOhXlPyJjGpKpNdUrZoOqUoUbWvXiSiQwSrUgJlJbHsAhSaWfFdHwJoLgTxUjXjZsBrQxLjPkSyEdHtLeUvHkHcTuScSvEhQiVaBdXgIrPpVhXtGsDtEsNtTrJlCpNpClKqFxVgDwKsEaEdQnJwAxSzFeImAdXaOjOxWaDaHgMmCmEqFzCsScUfPkMuKrExDsAuKlTyWtAqMnQhLhQgCjWzYrLmZyHsElJiPmFiToGqHwZzRbObAlItJsElXsPzVwZkGkJkJjOfPjHqKhEpTdCiJiHhKaRhCgKuFkDyVjKfRyMcTdRzNpJiWhZwRgAuPjAjQjApMxXrZfZeEkEtPfFzZmSyDuJpIkNpOoKeLdHePtKqJcFbDlYeObXhXuZvXiLlNyGkPcXiMlQwScJzXcFrPkWqKbKoRbAbVgDwTuEqWfCmNjJhSqVuBqGgMmHgRiBlMuMzCvGpQtElWdZeGeKcZaGrKnOjZnOoAaWqTdDzJyFdPmUkMdOjPdQmJxXbOwZjAzAtUwHiDwIyOeOvSrEcDnOsKrHoTyGrIfMcKuXxFwUuArKvNyDtIsWdYnXgQfQmCvBqGoCsFdQmLmWjJbZuRbPaKfKiIbJlLtDvGqUtIyFmLrIyYtCpSiTaLlFvNwRqHmLhIhGqCdKsBqVfOwDuJgFlOvGnMrGmEsCsTqQmVrWeGfViQpKsAiCqIcUhAlSfOoQcLjVvHoMwNaMaQiWgJbErIvOvVlLdOySaShRaJxGcZvDsHmFxSePkUxFkGcNnJyQlBnFvEoGwQqVuYxDbShBlDvCaAoGoNvKnOnKgNkWsSkLhDcZmVxTvBvXrJiQsCeWvBrAlGpMoNrSxOcOdTiFpQjAaJgAaFyItYbRfInAaJxUwEjFeJdTqVbTfRqLcSjBbDlEoQeGvWgUnNaGiNtQeFoHbFpSyIoNaClHjNwVoHxXxNgLjSjIxUvGfIaChWtGiIbNqIeYsLzKpFcXvEyWwItAyFdBbQlRsBjFqDaCtPlFvAiDeDvUgTvFvLxIgLhOmFqZpXyBtOrMyZrMbIyYuXgCbWkGsHnCkKmHnSaExLoQaNjPuYcWzJbUmSnJqZlDaMg";
        smsMobilesTemplate.setMobiles(mobiles);        

        dao.saveSmsMobilesTemplate(smsMobilesTemplate);

        // verify a primary key was assigned
        assertNotNull(smsMobilesTemplate.getId());

        // verify set fields are same after save
        assertEquals(mobiles, smsMobilesTemplate.getMobiles());
    }

    public void testGetSmsMobilesTemplate() throws Exception {
        SmsMobilesTemplate smsMobilesTemplate = dao.getSmsMobilesTemplate(smsMobilesTemplateId);
        assertNotNull(smsMobilesTemplate);
    }

    public void testGetSmsMobilesTemplates() throws Exception {
        SmsMobilesTemplate smsMobilesTemplate = new SmsMobilesTemplate();

        List results = dao.getSmsMobilesTemplates(smsMobilesTemplate);
        assertTrue(results.size() > 0);
    }

    public void testSaveSmsMobilesTemplate() throws Exception {
        SmsMobilesTemplate smsMobilesTemplate = dao.getSmsMobilesTemplate(smsMobilesTemplateId);

        // update required fields
        java.lang.String mobiles = "JeOnAvFxLqHsHdNvFxVvEpWuCvHlPyHaFlWfYlRxPdHeTlGvNfIjKzRtYrBsEmHhFxUqIqUxFxHlUuPoHdFpOtPcRcWfJqDmLqNvRyBmWdKbIrBeBdWdOrIrBsQlRhRgVjTeHpHwWnDzNqDpAkZlFwHdLiWaZnXhCqOpYpRiMoGqNkVuUaCzUpFhZxPgKhWiDlMwZgVaVuKbBfWnIyCvNlVmYiGuEjPiUcYkOyLbQnTjXfVdEnMiOpDfEuZxWmWcNfPaEcYvPsRoViZtCgDjGnTcZgItVwNvRdJcKjMoQaAiOjQlPrFfGqKdDfQxBqSfUkKbCcEbThOiDbScCnNnMrYyFrJgXxVtGoNxKcMuUbNsRzKcFfPsXuGsGmGbNyAbDhPgOpFeUnNsZzNnZoAhPgWtJuViGoVsJtNhLlDeGsQrTjJmZqPuGjHcXsEgFrEhZbIjToNbZrZhYfStBbFtXmBnVnYiOjLlXdKnEuMnZiBwHwFqPjXyPiUpEsOcJzYpUvAjCrSuMhWkEtSzFtVwWqOhWsXrIlQmAcIiDyMlRvBdSjKtGyUrTcJkVjWkKdWiEvJyHrWbAnBzFoNkXmBbVhFuHgMmSpTySiGoIgFgPqBbMcVzOcElVbAeGrDeWuMgRpZgBfZbYeQeZoCzBlFuYfDzMiGdJnZbZtDxKqMkPzAwSdZaQaDaGjWrIyRyIbXzQfWqFiJzZyKuRcUcVjUjEwFyGjDaMaSvPxJlOwYhYyRwIhIvUmKjQkHoHwEpIqFwHdMcJiBvIkGfOvTeSoFnAiIgUnUeQaAtBaIiEsXuDtFcTjAePxPdSuOwSxGzEaUmKvOeCeJwKlQaBaHiHdAdPnWvPkYyRxZmNcPoBzKcAvJfAhYlSqUmEnBuKeVpYrTiKnFjOlYlXbVlMqIzGeJrMiCnFsVoPlDjQfLmDySdObEvOlIeSaUoReUgHsQdKcKqDnMbRqNpOxOgAmTsExKfOvQvJzQzOfWgNnGmLbUkSsAmAlEsGqRhQcVrGrJjTcOuYjTyVfErBeDgSnXrPtQrBjUmRyVjRlUbNmZtZqQpLxIaMrMyRjCaFmPnGmJoTvCcJcBsWxRdDuDdFuYiSoMxHvZbTrRtAoHlKbKuQnTeQoWkZhWxBqSbEpIzXwYfNaCmEcNxArBxErNvTtFfNvLzRpDuXtNwMyRlZvQeFpMeVoAkRgUmElOmVwNrSePwJdOcHpWhTzWjJvKmEjRlMtTaRyDbEgHoQiWeFcNeBuMqGbNdRtCzIrPrZcHgTmTlXmQxOpRtRaRwPnAxOdKlRiQxPaEgFfWqRzOnWdBjGeAmVlLtZdKwFdXbRoTcSqXgTlZmVeYvMqKgEiNhKpVqLuRtNaFdEnNkJqWqThSjJjOdVpZeGeIjXvItHhMxSpBiMpJpSlHiWuUuXmFwMcHzOkLuImIvQcEmYyMtKeZlLhZaAcQqXeFdSzPdIaAmHlIiPtXwRaPdJlUqXxXpMdIuXvRuJiEdMgBfYhGtAsRfVtDjKgQkXdFaEgApJnYgDpCdMiMkQwEtKvYmPjJuJoDgOjRxPxInWpXnEiOyKnHfBhCwFjNmSaYnWySeYtSzHzNjDjHnGpLxYqUiMsXrVqAcOuZfLlUmBjLmPuQoWuPpWnTgVuHeEqTnGaBfLwSkTeAgErAhNqAkSdHcGxKhOdTkYvMmNyHmImVbFxBlDsWyDeAyPfZsJdCcVlDzCnVzOnOaTdMiUiKqVjHfKhDwVuYbMfYqVlThZuZqCkFwEjIoOgLvUqBcRyDyUeZjUzWkRiDyLpXiSeGeWeVkMrWrTaMnUsMrKgRyBdZuCrNyIcQnTiLvNtSoMyYdBlHmAfXiWaCnFjIvCwTpTmNdSpIqXmBkAmTbJvNeNoPuXbZlWpEbDoUmSxLhIsYwCvNkSeSaPjQoFaAvWpEbOnEnBiVyHyHhBbRqVhGgVmPnVuNmFyQkUuYvUjIdMpMyYkLuJwDdYkEpSvYjXzCzQsXwAcIrIqKfAsOzRjMlCuDvYkLaSaDhJzRvWkKeFyAnAaOmPmObMwTnCuDgYnYoFcTeRhDqSgNjPhQkQpEwZzFkQrVrNjZrBcEcKoGmLeQtChLgIrGxIgLkRuAuOmHsPjAeLoBtJhCvPtMhBkUzKnCjVwXfQoSlKhRpIqEqRyMoCwFkSnVaVvTdPuYgZgKkVcRnCaDzGyFcCzHzYkXiQsPwSxMlNiVyHgSjSaVnClAzFnXlSyQoPaAvVxOsEjOpUuTvRjRuJnFwGfNjSnNgEaSvRnGoLpYuUdQxYtLwFkRgMkVsLoCjLlXuBuBpLtKrZbUdGyYwMmVqDzAkFaJjPfVpJjMdXsKkBwBeJrIaYfAaBcCbSeGiFaPaKkYjPuLhOwEpZeJvKoXiVlRaNvOjLmCkHfBmYrMrJfBzKqFnNhIkXtXoEpAiSgKwPoAgVoLuBqQeYjEtMaLaDkQoBoSrHqDlZcEoVfZtWzUvHpVsFaWiQjZbCaRsOhFuTwBsMyTeMkOfDdNaSrIlPnWnFtQaJgAaXlNkAyNoNgTjDvZoNzKiRxCxHeGbHdPvGxEiCuWlZyNfEmDuSgPxJoLhLbMeUtBqCiYyAmZlCiJgSaIdVfOiYjGpDiAtZtNlSlDiEdRnCzMjMwPtKiTsEiZqEfIoFrZjLmOpLtWxYbDtLnMkKeFzVzHjHoRpKgRbWnXfGhYsJgLiZmUuDnKxAbDlOlNwYuLrGpKxRzUhAzHgGkRtJcWpClGzSzBqTbStKqWmSySmHwRtZjGmNxLzLlVmQlCvOfJxKhSgZyViSlAcKrYvMxFsXaPgOsGsPbYyBaLiQlRzKeDbIoQbGsNjCtWvYqDvFpIfDoImKaYwVsAfRkRyErIsLqZtCdEcYtKbGoHdXxQxYeBaBdEuDzPiVeDsQeGkCiDwWoEnMpImJfSrWwXzHnHoCcHoOcZeXjQaIcYhWhWzZoCmKqYtXiElMtFcHhXgSoAgKcPdKwZePqXkDuXyGxIhHgFsDaId";
        smsMobilesTemplate.setMobiles(mobiles);        

        dao.saveSmsMobilesTemplate(smsMobilesTemplate);

        assertEquals(mobiles, smsMobilesTemplate.getMobiles());
    }

    public void testRemoveSmsMobilesTemplate() throws Exception {
        String removeId = new String("3");
        dao.removeSmsMobilesTemplate(removeId);
        try {
            dao.getSmsMobilesTemplate(removeId);
            fail("smsMobilesTemplate found in database");
        } catch (ObjectRetrievalFailureException e) {
            assertNotNull(e.getMessage());
        }
    }
}
