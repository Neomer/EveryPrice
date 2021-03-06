﻿using Neomer.EveryPrice.SDK.Helpers;
using Neomer.EveryPrice.SDK.Models;
using Neomer.EveryPrice.SDK.Session;
using NHibernate;
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

		public override void Save(ISession session, IEntity entity)
		{
			var tag = entity as ITag;
			if (tag != null)
			{
				if (tag.Value.Length < 3)
				{
					throw new FormatException("Tag name is too short");
				}
			}

			base.Save(session, entity);
		}

		/// <summary>
		/// Возвращает тэг по его полному значению, либо null
		/// </summary>
		/// <param name="value">Значение тэга</param>
		/// <returns></returns>
		public ITag FindTag(ISession session, string value)
        {
            return session
				.CreateCriteria<ITag>()
                .Add(Expression.Eq("Value", value))
                .UniqueResult<ITag>();
        }

        /// <summary>
        /// Ищет тэги, похожие на указанное значение
        /// </summary>
        /// <returns></returns>
        public IList<Tag> FindTags(ISession session, string value)
        {
            var validator = new Regex(@"[\(\)\s\;\,\.\\]");

            if (validator.Match(value).Success)
            {
                throw new FormatException();
            }

            return session
				.CreateCriteria<ITag>()
                .Add(Expression.Like("Value", String.Format("%{0}%", value)))
                .SetMaxResults(10)
                .List<Tag>();
        }
    }
}
