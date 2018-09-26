using Neomer.EveryPrice.SDK.Helpers;
using Neomer.EveryPrice.SDK.Models;
using NHibernate.Criterion;
using System;
using System.Collections.Generic;
using System.Text.RegularExpressions;

namespace Neomer.EveryPrice.SDK.Managers
{
    public class TagManager : BaseEntityManager<TagManager, ITag>
    {

        protected TagManager()
        {

        }

        /// <summary>
        /// Возвращает тэг по его полному значению, либо null
        /// </summary>
        /// <param name="value">Значение тэга</param>
        /// <returns></returns>
        public ITag FindTag(string value)
        {
            return NHibernateHelper.Instance.CurrentSession.CreateCriteria<ITag>()
                .Add(Expression.Eq("Value", value))
                .UniqueResult<ITag>();
        }

        /// <summary>
        /// Ищет тэги, похожие на указанное значение
        /// </summary>
        /// <returns></returns>
        public IList<Tag> FindTags(string value)
        {
            var validator = new Regex(@"[\(\)\s\;\,\.\\]");

            if (validator.Match(value).Success)
            {
                throw new FormatException();
            }

            return NHibernateHelper.Instance.CurrentSession.CreateCriteria<ITag>()
                .Add(Expression.Like("Value", String.Format("%{0}%", value)))
                .SetMaxResults(10)
                .List<Tag>();
        }
    }
}
